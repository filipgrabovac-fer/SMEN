import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostLoginProps = {
  username: string;
  password: string;
};
export const usePostLogin = () => {
  return useMutation({
    mutationFn: async ({ username, password }: PostLoginProps) => {
      const response = await customFetch({
        endpointUrl: "http://localhost:8080/api/keycloak/login",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username: username,
          password: password,
          client_id: "smen-rest-api",
          grant_type: "password",
        }),
      });

      console.log(response);
      return response;
    },
  });
};
