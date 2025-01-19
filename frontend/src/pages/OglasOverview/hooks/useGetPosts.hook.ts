import { useQuery } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type GetPostType = {
  description: string;
  id: number;
  tags: string;
  title: string;
  author: string;
  createdAt: string;
};
export const useGetPosts = () => {
  return useQuery<GetPostType[]>({
    queryKey: ["posts"],
    queryFn: async () => {
      const response = await customFetch({
        endpointUrl: "post",
        method: "GET",
      });
      return response;
    },
  });
};
