import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetThemeDataType = {
  description: string;
  id: number;
  tags: string;
  title: string;
};
export const useGetThemes = () => {
  return useQuery<GetThemeDataType[]>({
    queryKey: ["themes"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: "subject",
        method: "GET",
      });
      return response;
    },
  });
};
