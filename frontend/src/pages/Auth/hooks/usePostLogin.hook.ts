import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostLoginMutationProps = {
  username: string;
  password: string;
};

export type PostLoginProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PostLoginMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostLogin = ({ onSuccess }: PostLoginProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({ username, password }: PostLoginMutationProps) => {
      const response = await customFetch({
        endpointUrl: "keycloak/login",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          username,
          password,
          client_id: "smen-rest-api",
          grant_type: "password",
        }),
      });

      localStorage.setItem("token", response.access_token);
      localStorage.setItem("userRole", response.role);
      localStorage.setItem("userId", response.userId);

      return response;
    },
  });
};
