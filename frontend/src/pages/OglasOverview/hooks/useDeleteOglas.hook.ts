import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type DeleteOglasProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: DeleteOglasMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export type DeleteOglasMutationProps = {
  oglasId: number;
};
export const useDeleteOglas = ({ onSuccess }: DeleteOglasProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({ oglasId }: DeleteOglasMutationProps) => {
      const response = await customFetch({
        endpointUrl: `post/${oglasId}`,
        method: "DELETE",
        headers: {
          "Content-Type": "application/json",
        },
      });
      return response;
    },
  });
};
