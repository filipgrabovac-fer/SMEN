import { useState } from "react";
import { usePostLogin } from "./hooks/usePostLogin.hook";
import { useNavigate } from "@tanstack/react-router";
import { Button, Input } from "antd";
import { registerRoute } from "../../routes/auth/register.routes";
import { themesRoute } from "../../routes/themes/themes.routes";

export type HandleSubmitProps = {
  username: string;
  password: string;
};

export const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { mutate: postLogin } = usePostLogin({
    onSuccess: () => {
      navigate({ to: themesRoute.fullPath });
    },
  });

  const navigate = useNavigate();

  return (
    <div className="flex flex-col justify-center w-screen h-screen p-4 ">
      <div className="flex flex-col items-center gap-y-4">
        <p className="text-2xl "> Prijava</p>
        <div className="bg-gray-100 w-max p-4 m-auto rounded-md">
          <div className="flex flex-col w-[300px] m-auto gap-y-2">
            <Input
              name="username"
              placeholder="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <Input
              placeholder="password"
              typeof="password"
              name="password"
              value={password}
              type="password"
              onChange={(e) => setPassword(e.target.value)}
            />

            <Button onClick={() => postLogin({ username, password })}>
              Prijava
            </Button>
          </div>
        </div>
        <div className="flex gap-x-2">
          <p>Nemaš račun? </p>
          <p
            className="underline text-button_border cursor-pointer"
            onClick={() => navigate({ to: registerRoute.fullPath })}
          >
            Registriraj se
          </p>
        </div>
      </div>
    </div>
  );
};
