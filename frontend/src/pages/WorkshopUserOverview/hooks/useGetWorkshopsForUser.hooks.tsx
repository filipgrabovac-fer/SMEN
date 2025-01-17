import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetWorkshopsForUserMutationProps = {
  userId: number;
};

export type GetWorkshopsForUserDataType = {
  description: string;
  duration: number;
  id: number;
  noOfAvailableSlots: number;
  ownerId: number;
  title: string;
  workshopStatusName: string;
};
export const useGetWorkshopsForUser = ({
  userId,
}: GetWorkshopsForUserMutationProps) => {
  return useQuery<GetWorkshopsForUserDataType[]>({
    queryKey: ["workshops"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `workshop/user/${userId}`,
        method: "GET",
      });

      return response;
    },
  });
};
