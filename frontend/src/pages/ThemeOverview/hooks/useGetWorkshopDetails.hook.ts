import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetWorkshopDetailsDataType = {
  description: string;
  duration: number;
  id: number;
  noOfAvailableSlots: number;
  ownerId: number;
  title: string;
  hasApplied: boolean;
};

export type GetWorkshopDetailsProps = {
  workshopId: string;
};

export const useGetWorkshopDetails = ({
  workshopId,
}: GetWorkshopDetailsProps) => {
  const userId = Number(localStorage.getItem("userId") ?? 0);

  return useQuery<GetWorkshopDetailsDataType>({
    queryKey: ["workshop-details"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `workshop/${workshopId}/user/${userId}`,
        method: "GET",
      });

      return response;
    },
  });
};
