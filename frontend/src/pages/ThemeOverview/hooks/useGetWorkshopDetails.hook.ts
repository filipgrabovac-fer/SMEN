import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetWorkshopDetailsDataType = {
  description: string;
  duration: number;
  id: number;
  noOfAvailableSlots: number;
  ownerId: number;
  title: string;
};

export type GetWorkshopDetailsProps = {
  workshopId: string;
};

export const useGetWorkshopDetails = ({
  workshopId,
}: GetWorkshopDetailsProps) => {
  return useQuery<GetWorkshopDetailsDataType>({
    queryKey: ["workshop-details"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `workshop/${workshopId}`,
        method: "GET",
      });

      return response;
    },
  });
};
