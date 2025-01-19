import { Modal, Spin } from "antd";
import { useGetWorkshopDetails } from "../hooks/useGetWorkshopDetails.hook";
import { Dispatch, SetStateAction } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { usePostWorkshopApplication } from "../hooks/usePostWorkshopApplication.hook";

export enum WorkshopApplicationUserTypeEnum {
  USER = "user",
  MENTOR = "mentor",
}

export type WorkshopApplicationModalProps = {
  isModalOpen: boolean;
  setIsModalOpen: Dispatch<SetStateAction<boolean>>;
  selectedWorkshopId: number;
  setSelectedWorkshopId: Dispatch<SetStateAction<boolean | undefined>>;
};

export type HandleApplicationsProps = {
  type: WorkshopApplicationUserTypeEnum;
};

export const WorkshopApplicationModal = ({
  isModalOpen,
  setIsModalOpen,
  selectedWorkshopId,
  setSelectedWorkshopId,
}: WorkshopApplicationModalProps) => {
  const { data, isLoading } = useGetWorkshopDetails({
    workshopId: selectedWorkshopId.toString(),
  });

  const queryClient = useQueryClient();
  const { mutate: postWorkshopApplucation } = usePostWorkshopApplication({
    onSuccess: () => {
      queryClient.invalidateQueries({
        queryKey: ["workshops"],
      });
      setIsModalOpen(false);
      setSelectedWorkshopId(undefined);
    },
  });

  return (
    <Modal
      title={data?.title}
      footer={null}
      open={isModalOpen}
      centered
      width={"60%"}
      onCancel={() => {
        setIsModalOpen(false);
        setSelectedWorkshopId(undefined);
      }}
    >
      <div className="flex flex-col gap-y-4">
        <p className="mt-6">{data?.description}</p>
        <div className="mb-8">
          <p className="font-medium">Datum održavanja: {data?.duration}</p>
          <p className="font-medium">
            Broj slobodnih mjesta: {data?.noOfAvailableSlots}
          </p>
        </div>
      </div>

      {data?.hasApplied ? (
        <p className="text-red-500 m-auto w-max">
          Već ste se prijavili na ovu radionicu
        </p>
      ) : (
        <div className="flex gap-x-4 justify-center">
          {data && !isLoading ? (
            <button
              className="border border-button_border rounded-md p-2 hover:opacity-60 text-button_border"
              onClick={() =>
                postWorkshopApplucation({ workshopId: selectedWorkshopId })
              }
            >
              Prijavi se
            </button>
          ) : (
            <Spin className="w-3 h-3" />
          )}
        </div>
      )}
    </Modal>
  );
};
