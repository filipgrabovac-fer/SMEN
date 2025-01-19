import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostApproveMentorRequestMutationProps = {
  mentorRequestId: number;
};
export type PostApproveMentorRequestProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PostApproveMentorRequestMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostApproveMentorRequest = ({
  onSuccess,
}: PostApproveMentorRequestProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      mentorRequestId,
    }: PostApproveMentorRequestMutationProps) => {
      const userId = localStorage.getItem("userId");
      const response = await customFetch({
        method: "POST",
        endpointUrl: `mentor-request/${mentorRequestId}/approve/user/${userId}`,
      });
      return response;
    },
  });
};
