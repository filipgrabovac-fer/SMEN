import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostNewThemeMutationProps = {
  id: number;
  title: string;
  tags: string;
  description: string;
};

export type PostNewThemeProps = {
  onSuccess:
    | ((
        data: any,
        variables: PostNewThemeMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostNewTheme = ({ onSuccess }: PostNewThemeProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      description,
      id,
      tags,
      title,
    }: PostNewThemeMutationProps) => {
      const response = await customFetch({
        endpointUrl: "subject/new",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          description,
          id,
          tags,
          title,
        }),
      });

      console.log(response);
      return response;
    },
  });
};
