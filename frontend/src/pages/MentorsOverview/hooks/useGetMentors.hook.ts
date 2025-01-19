import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetMentorDataType = {
  id: number;
  comment: string;
  email: string;
  firstName: string;
  lastName: string;
  status: string;
  createdAt: string;
};
export const useGetMentors = () => {
  return useQuery<GetMentorDataType[]>({
    queryKey: ["mentorRequests"],
    queryFn: async () => {
      const response = await customFetch({
        method: "GET",
        endpointUrl: "mentor-request",
      });

      return response;
    },
  });
};
