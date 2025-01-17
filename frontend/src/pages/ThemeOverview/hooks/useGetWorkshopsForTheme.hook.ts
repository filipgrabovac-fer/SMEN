import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetWorkshopsForThemeMutationProps = {
  subjectId: string;
};

export type GetWorkshopsForThemeDataType = {
  description: string;
  duration: number;
  id: number;
  noOfAvailableSlots: number;
  ownerId: number;
  title: string;
  workshopStatus: string;
  dateOfEvent: string;
};
export const useGetWorkshopsForTheme = ({
  subjectId,
}: GetWorkshopsForThemeMutationProps) => {
  return useQuery<GetWorkshopsForThemeDataType[]>({
    queryKey: ["workshops"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `workshop/subject/${subjectId}`,
        method: "GET",
      });

      console.log(response);
      return response;
    },
  });
};
