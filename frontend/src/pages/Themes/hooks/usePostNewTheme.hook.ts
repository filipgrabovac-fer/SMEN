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
        data: unknown,
        variables: PostNewThemeMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostNewTheme = ({ onSuccess }: PostNewThemeProps) => {
  // // @ts-expect-error: userId is always defined at this point
  // const { userId } = jwtDecode(localStorage.getItem("token") ?? "");
  const userId = Number(localStorage.getItem("userId") ?? 0);

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
          authorId: userId,
        }),
      });

      return response;
    },
  });
};
