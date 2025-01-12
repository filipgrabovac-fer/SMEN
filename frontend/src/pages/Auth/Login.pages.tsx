import { useState } from "react";
import { usePostLogin } from "./hooks/usePostLogin.hook";
import { useGetCookieContent } from "./hooks/useGetCookieContent.hook";

export type HandleSubmitProps = {
  username: string;
  password: string;
};

export const Login = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const { mutate: postLogin } = usePostLogin();

  const { mutate: getCookie } = useGetCookieContent();
  return (
    <div className="flex flex-col justify-center w-screen h-screen bg-red-800 p-4">
      <div className="flex flex-col w-[400px] m-auto ">
        <input
          name="username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          name="password"
          value={password}
          type="password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button onClick={() => postLogin({ username, password })}>Login</button>
      </div>
      <button onClick={() => getCookie()}>get Cookie</button>
    </div>
  );
};
