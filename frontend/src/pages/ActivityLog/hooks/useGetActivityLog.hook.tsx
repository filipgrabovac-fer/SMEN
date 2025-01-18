import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetActivityLogMutationProps = {};

export type GetActivityLogDataType = {
  description: string;
  userId: number;
  activity: string;
  id: number;
};
export const useGetActivityLog = ({}: GetActivityLogMutationProps) => {
  return useQuery<GetActivityLogDataType[]>({
    queryKey: ["activity-log"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: `activity`,
        method: "GET",
      });
      return response;
    },
  });
};
