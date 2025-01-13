import { Modal } from "antd";

import { Dispatch, SetStateAction } from "react";
import { useGetWorkshopDetails } from "../../ThemeOverview/hooks/useGetWorkshopDetails.hook";

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

export const WorkshopDetailsModal = ({
  isModalOpen,
  setIsModalOpen,
  selectedWorkshopId,
  setSelectedWorkshopId,
}: WorkshopApplicationModalProps) => {
  const title = "Lorem ipsum";
  const date = "2021-09-09";
  const numberOfApplicants = 100;
  const type = "Lorem ipsum";

  const { data } = useGetWorkshopDetails({
    workshopId: selectedWorkshopId.toString(),
  });

  console.log(data);

  return (
    <Modal
      title={title}
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
        <p className="mt-6">
          Lorem ipsum dolor sit amet consectetur adipisicing elit. Optio dolor
          cupiditate quasi! Veritatis natus ea aperiam temporibus rem molestiae
          neque laboriosam voluptate repudiandae nobis sint soluta voluptatem
          placeat, consequatur dicta possimus eum quidem ipsa iure nemo ipsam
          laborum odit iusto facilis. Maxime exercitationem, consequatur ratione
          rem debitis ab ut consectetur adipisci fugit provident error
          asperiores pariatur molestiae laudantium voluptatem vero voluptates
          quaerat est delectus!
        </p>
        <div className="mb-8">
          <p className="font-medium">Datum održavanja: {date}</p>
          <p className="font-medium">Broj sudionika: {numberOfApplicants}</p>
          <p className="font-medium">Tip: {type}</p>
        </div>
      </div>
    </Modal>
  );
};
