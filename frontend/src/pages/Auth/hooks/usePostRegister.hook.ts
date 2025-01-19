import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostRegisterMutationProps = {
  username: string;
  password: string;
  email: string;
  firstName: string;
  lastName: string;
  roleId: number;
};

export type PostRegisterProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PostRegisterMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostRegister = ({ onSuccess }: PostRegisterProps) => {
  return useMutation({
    onSuccess: onSuccess,
    onError: (error) => {
      console.log("error", error);
    },
    mutationFn: async ({
      email,
      firstName,
      lastName,
      password,
      username,
      roleId,
    }: PostRegisterMutationProps) => {
      const response = await customFetch({
        endpointUrl: "keycloak/register",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          email: email,
          firstName,
          lastName,
          password,
          username,
          roleId,
        }),
      });

      localStorage.setItem("token", response.access_token);
      localStorage.setItem("userRole", response.role);
      localStorage.setItem("userId", response.userId);
      return response;
    },
  });
};
