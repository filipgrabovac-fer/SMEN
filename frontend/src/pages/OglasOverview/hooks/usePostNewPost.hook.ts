import { useMutation } from "@tanstack/react-query";
import { NewPostType } from "../components/AddOglas";
import { customFetch } from "../../../utils/customFetch";

export type PostNewPostProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: NewPostType,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostNewPost = ({ onSuccess }: PostNewPostProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({ description, title }: NewPostType) => {
      const response = await customFetch({
        endpointUrl: "post",
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          description,
          title,
          userId: 1,
        }),
      });

      return response;
    },
  });
};
