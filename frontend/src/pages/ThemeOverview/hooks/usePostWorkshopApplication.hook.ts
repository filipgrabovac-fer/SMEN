import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostWorkshopApplicationMutationProps = {
  workshopId: number;
};

export type PostWorkshopApplicationProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PostWorkshopApplicationMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostWorkshopApplication = ({
  onSuccess,
}: PostWorkshopApplicationProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      workshopId,
    }: PostWorkshopApplicationMutationProps) => {
      const userId = 1;
      const response = await customFetch({
        endpointUrl: `workshop-application/workshop/${workshopId}/user/${userId}`,
        method: "POST",
      });
      return response;
    },
  });
};
