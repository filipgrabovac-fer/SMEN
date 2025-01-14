import { useState } from "react";
import { usePostLogin } from "./hooks/usePostLogin.hook";
import { useNavigate } from "@tanstack/react-router";
import { homeRoute } from "../../routes/home/home.routes";
import { Button, Input } from "antd";
import { loginRoute } from "../../routes/auth/login.routes";

export type HandleSubmitProps = {
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
};

export const Register = () => {
  const [username, setUsername] = useState("");
  const [email, setEmail] = useState("");
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [password, setPassword] = useState("");

  console.log(email, firstName, lastName, password, username);
  const { mutate: postLogin } = usePostLogin({
    onSuccess: () => {
      navigate({ to: homeRoute.fullPath });
    },
  });

  const navigate = useNavigate();

  return (
    <div className="flex flex-col justify-center w-screen h-screen p-4 ">
      <div className="flex flex-col items-center gap-y-4">
        <p className="text-2xl "> Registracija</p>
        <div className="bg-gray-100 w-max p-4 m-auto rounded-md">
          <div className="flex flex-col w-[300px] m-auto gap-y-2">
            <Input
              hidden
              name="username"
              placeholder="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <Input
              name="ime"
              placeholder="ime"
              value={username}
              onChange={(e) => setFirstName(e.target.value)}
            />
            <Input
              name="prezime"
              placeholder="prezime"
              value={username}
              onChange={(e) => setLastName(e.target.value)}
            />
            <Input
              name="email"
              placeholder="email"
              value={username}
              onChange={(e) => setEmail(e.target.value)}
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
              Registracija
            </Button>
          </div>
        </div>
        <div className="flex gap-x-2">
          <p>Imaš račun? </p>
          <p
            className="underline text-button_border cursor-pointer"
            onClick={() => navigate({ to: loginRoute.fullPath })}
          >
            Prijavi se
          </p>
        </div>
      </div>
    </div>
  );
};
