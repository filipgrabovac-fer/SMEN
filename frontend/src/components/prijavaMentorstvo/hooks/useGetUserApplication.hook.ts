import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export const useGetUserApplication = () => {
  return useQuery({
    queryKey: ["getUserApplication"],
    queryFn: async () => {
      const userId = localStorage.getItem("userId");
      const response = await customFetch({
        method: "GET",
        endpointUrl: `mentor-request/requester/${userId}`,
      });

      return response.length > 0;
    },
  });
};
