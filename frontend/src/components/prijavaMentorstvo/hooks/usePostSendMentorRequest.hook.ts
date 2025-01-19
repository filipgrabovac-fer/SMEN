import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostSendMentorRequestMutationProps = {
  comment: string;
  requesterId: number;
};
export type PostSendMentorRequestProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PostSendMentorRequestMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostSendMentorRequest = ({
  onSuccess,
}: PostSendMentorRequestProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      comment,
      requesterId,
    }: PostSendMentorRequestMutationProps) => {
      const userId = localStorage.getItem("userId");
      const response = await customFetch({
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ comment: comment, requesterId: requesterId }),
        endpointUrl: `mentor-request/user/${userId}`,
      });
      return response;
    },
  });
};
