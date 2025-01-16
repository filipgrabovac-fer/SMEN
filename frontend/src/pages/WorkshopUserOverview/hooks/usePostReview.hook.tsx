import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostReviewMutationProps = {
  rating: number;
  comment: string;
  userId: number;
  workshopId: number;
};

export type PostReviewProps = {
  onSuccess:
    | ((
        variables: PostReviewMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostReview = ({ onSuccess }: PostReviewProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      rating,
      comment,
      userId,
      workshopId,
    }: PostReviewMutationProps) => {
      const response = await customFetch({
        endpointUrl: `rating/new`,
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: rating,
          comment: comment,
          userId: userId,
          workshopId: workshopId,
        }),
      });
      return response;
    },
  });
};
