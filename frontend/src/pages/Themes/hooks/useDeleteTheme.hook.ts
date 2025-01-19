import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type DeleteThemeMutationProps = {
  themeId: number;
};

export type DeleteThemeProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: DeleteThemeMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};
export const useDeleteTheme = ({ onSuccess }: DeleteThemeProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({ themeId }: DeleteThemeMutationProps) => {
      const userId = localStorage.getItem("userId");
      const response = await customFetch({
        endpointUrl: `subject/${themeId}/user/${userId}`,
        method: "DELETE",
      });
      return response;
    },
  });
};
