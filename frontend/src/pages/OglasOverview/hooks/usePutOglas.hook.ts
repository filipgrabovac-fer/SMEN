import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PutOglasMutationProps = {
  title: string;
  description: string;
  postId: number;
};

export type PutOglasProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PutOglasMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};
export const usePutOglas = ({ onSuccess }: PutOglasProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      title,
      description,
      postId,
    }: PutOglasMutationProps) => {
      const response = await customFetch({
        endpointUrl: `post/${postId}`,
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title,
          description,
          userId: 1,
        }),
      });

      return response;
    },
  });
};
