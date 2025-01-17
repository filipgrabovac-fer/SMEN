import { useMutation } from "@tanstack/react-query";
import { customFetch } from "../../../utils/customFetch";

export type PostWorkshopMutationProps = {
  workshopTitle: string;
  workshopDescription: string;
  subjectId: number;
  workshopDateOfEvent: string;
};

export type PostWorkshopProps = {
  onSuccess:
    | ((
        variables: PostWorkshopMutationProps,
        context: unknown
      ) => Promise<unknown> | unknown)
    | undefined;
};

export const usePostWorkshop = ({ onSuccess }: PostWorkshopProps) => {
  // // @ts-expect-error: userId is always defined at this point
  // const { userId } = jwtDecode(localStorage.getItem("token") ?? "");
  const userId = 1;

  return useMutation({
    onSuccess: onSuccess,
    mutationFn: async ({
      workshopTitle,
      workshopDescription,
      subjectId,
      workshopDateOfEvent,
    }: PostWorkshopMutationProps) => {
      const response = await customFetch({
        endpointUrl: `workshop`,
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          dateOfEvent: workshopDateOfEvent.toString().replace("Z", ""),
          title: workshopTitle,
          description: workshopDescription,
          userId,
          subjectId: subjectId,
        }),
      });
      return response;
    },
  });
};
