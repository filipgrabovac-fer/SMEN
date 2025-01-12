import { useState } from "react";
import { customFetch } from "../../utils/customFetch";

export type HandleSubmitProps = {
  username: string;
  password: string;
};

const handleSubmit = async () => {
  const response = await customFetch({
    endpointUrl: "http://localhost:8080/api/keycloak/login",
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      username: "test_user",
      password: "test_user",
      client_id: "smen-rest-api",
      grant_type: "password",
    }),
  });
  console.log(response);
};

export const Home = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  return (
    <div className="flex flex-col justify-center w-screen h-screen">
      <div className="flex flex-col w-[400px] m-auto">
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

        <button onClick={() => handleSubmit()}>Submit</button>
      </div>
    </div>
  );
};
