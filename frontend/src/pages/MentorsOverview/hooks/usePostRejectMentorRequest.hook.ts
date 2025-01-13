import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostRejectMentorRequestMutationProps = {
  mentorRequestId: number;
};

export type PostRejectMentorRequestProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PostRejectMentorRequestMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostRejectMentorRequest = ({
  onSuccess,
}: PostRejectMentorRequestProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      mentorRequestId,
    }: PostRejectMentorRequestMutationProps) => {
      const response = await customFetch({
        method: "POST",
        endpointUrl: `mentor-request/${mentorRequestId}/reject`,
      });
      return response;
    },
  });
};
