import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export const useGetCookieContent = () => {
  return useMutation({
    mutationFn: async () => {
      const response = await customFetch({
        endpointUrl: "http://localhost:8080/api/keycloak/get-cookie-data",
        method: "GET",
      });

      return response;
    },
  });
};
