import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type EditThemeMutationProps = {
  themeTitle: string;
  themeDescription: string;
  subjectId: number;
};

export type EditThemeProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: EditThemeMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};
export const useEditTheme = ({ onSuccess }: EditThemeProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      themeTitle,
      themeDescription,
      subjectId,
    }: EditThemeMutationProps) => {
      const response = await customFetch({
        method: "PUT",
        endpointUrl: `subject/${subjectId}`,
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: themeTitle,
          description: themeDescription,
        }),
      });
      console.log(response);
      return response;
    },
  });
};
