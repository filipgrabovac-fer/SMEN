import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostWorkshopMutationProps = {
  workshopTitle: string;
  workshopDescription: string;
  workshopTags: string;
  subjectId: number;
};

export type PostWorkshopProps = {
  onSuccess:
    | ((
        variables: PostWorkshopMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostWorkshop = ({ onSuccess }: PostWorkshopProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      workshopTitle,
      workshopDescription,
      subjectId,
    }: PostWorkshopMutationProps) => {
      const response = await customFetch({
        endpointUrl: `workshop`,
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: workshopTitle,
          description: workshopDescription,
          userId: 1,
          subjectId: subjectId,
        }),
      });
      return response;
    },
  });
};
