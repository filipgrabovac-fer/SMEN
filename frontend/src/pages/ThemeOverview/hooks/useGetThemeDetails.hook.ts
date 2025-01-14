import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetThemeDetailsProps = {
  subjectId: string;
};
export type GetThemeDetailsDataType = {
  id: number;
  title: string;
  tags: string;
  description: string;
};

export const useGetThemeDetails = ({ subjectId }: GetThemeDetailsProps) => {
  return useQuery<GetThemeDetailsDataType>({
    queryKey: ["theme-details"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `subject/${subjectId}`,
        method: "GET",
      });

      return response;
    },
  });
};
