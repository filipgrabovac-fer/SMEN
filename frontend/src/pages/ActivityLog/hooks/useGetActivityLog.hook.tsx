import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetActivityLogDataType = {
  description: string;
  userId: number;
  activity: string;
  id: number;
  user: string;
  createdAt: string;
};
export const useGetActivityLog = () => {
  return useQuery<GetActivityLogDataType[]>({
    queryKey: ["activity-log"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `activity-log`,
        method: "GET",
      });
      return response;
    },
  });
};
