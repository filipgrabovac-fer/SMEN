import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type DeleteWorkshopMutationProps = { workshopId: number };
export type DeleteWorkshopProps = {
  onSuccess: (
    data: unknown,
    variables: DeleteWorkshopMutationProps,
    context: unknown
  ) => Promise<unknown> | unknown;
};
export const useDeleteWorkshop = ({ onSuccess }: DeleteWorkshopProps) => {
  const userId = localStorage.getItem("userId");

  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({ workshopId }: DeleteWorkshopMutationProps) => {
      const response = await customFetch({
        endpointUrl: `workshop/${workshopId}/user/${userId}`,
        method: "DELETE",
      });
      return response;
    },
  });
};
