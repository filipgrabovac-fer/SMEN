import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetPostType = {
  description: string;
  id: number;
  tags: string;
  title: string;
};
export const useGetPosts = () => {
  return useQuery<GetPostType[]>({
    queryKey: ["posts"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: "subject",
        method: "GET",
      });
      return response;
    },
  });
};
