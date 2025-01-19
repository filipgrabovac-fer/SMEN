import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PutWorkshopMutationProps = {
  workshopTitle: string;
  workshopDescription: string;
  workshopId: number;
  workshopStatusId: number;
  noOfAvailableSlots: number;
};

export type PutWorkshopProps = {
  onSuccess:
    | ((
        data: unknown,
        variables: PutWorkshopMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};
export const usePutWorkshop = ({ onSuccess }: PutWorkshopProps) => {
  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      workshopId,
      workshopTitle,
      workshopDescription,
      workshopStatusId,
      noOfAvailableSlots,
    }: PutWorkshopMutationProps) => {
      const response = await customFetch({
        endpointUrl: `workshop/${workshopId}`,
        method: "PUT",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          title: workshopTitle,
          description: workshopDescription,
          workshopStatusId: workshopStatusId,
          noOfAvailableSlots,
        }),
      });

      return response;
    },
  });
};
