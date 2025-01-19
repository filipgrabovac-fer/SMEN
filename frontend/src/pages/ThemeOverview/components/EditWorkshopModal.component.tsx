import { Form, Input, Modal, Select } from "antd";
import TextArea from "antd/es/input/TextArea";
import { Dispatch, SetStateAction } from "react";
import { usePutWorkshop } from "../hooks/usePutWorkshop.hook";
import { useQueryClient } from "@tanstack/react-query";

export enum WorkshopStatusEnum {
  PENDING = "PENDING",
  APPROVED = "APPROVED",
  SCHEDULED = "SCHEDULED",
  COMPLETED = "COMPLETED",
  CANCELED = "CANCELED",
  POSTPONED = "POSTPONED",
}

export type EditWorkshopModalProps = {
  workshopStatusId: number;
  setWorkshopStatusId: Dispatch<SetStateAction<number>>;
  selectedWorkshopId?: number;
  setSelectedWorkshopId: Dispatch<SetStateAction<number | undefined>>;
  isModalOpen: boolean | undefined;
  setIsModalOpen: Dispatch<SetStateAction<boolean | undefined>>;
  workshopTitle: string;
  workshopDescription: string;
  setWorkshopTitle: Dispatch<SetStateAction<string | undefined>>;
  setWorkshopDescription: Dispatch<SetStateAction<string | undefined>>;
  workshopNumberOfPlaces: number | undefined;
  setWorkshopNumberOfPlaces: Dispatch<SetStateAction<number | undefined>>;
};

export const EditWorkshopModal = ({
  workshopStatusId,
  selectedWorkshopId,
  setSelectedWorkshopId,
  isModalOpen,
  setIsModalOpen,
  workshopTitle,
  workshopDescription,
  setWorkshopDescription,
  setWorkshopTitle,
  setWorkshopStatusId,
  setWorkshopNumberOfPlaces,
  workshopNumberOfPlaces,
}: EditWorkshopModalProps) => {
  const queryClient = useQueryClient();
  const [form] = Form.useForm();
  const { mutate: putworkshop } = usePutWorkshop({
    onSuccess: () => {
      setSelectedWorkshopId(undefined);
      setIsModalOpen(undefined);
      queryClient.invalidateQueries({ queryKey: ["workshops"] });
    },
  });

  return (
    <Modal
      title="Uredi radionicu"
      centered
      open={isModalOpen}
      onCancel={() => {
        setSelectedWorkshopId(undefined);
        setIsModalOpen(undefined);
      }}
      onOk={() => {
        form.validateFields().then(() => {
          putworkshop({
            workshopTitle: workshopTitle,
            workshopDescription: workshopDescription,
            workshopId: selectedWorkshopId ?? 0,
            workshopStatusId: workshopStatusId,
            noOfAvailableSlots: workshopNumberOfPlaces ?? 0,
          });

          form.resetFields();
        });
      }}
    >
      <Form form={form} layout="vertical">
        <Form.Item>
          <Input
            allowClear
            placeholder="ime radionice"
            value={workshopTitle}
            onChange={(e) => setWorkshopTitle(e.target.value)}
          />
        </Form.Item>
        <Form.Item>
          <TextArea
            allowClear
            placeholder="opis radionice"
            rows={6}
            value={workshopDescription}
            onChange={(e) => setWorkshopDescription(e.target.value)}
          />
        </Form.Item>
        <Form.Item>
          <Input
            allowClear
            placeholder="broj mjesta"
            type="number"
            value={workshopNumberOfPlaces}
            onChange={(e) => setWorkshopNumberOfPlaces(Number(e.target.value))}
          />
        </Form.Item>
        <Form.Item name="workshopStatusId">
          <Select
            defaultValue={workshopStatusId}
            onSelect={(value) => setWorkshopStatusId(value)}
            options={[
              { label: WorkshopStatusEnum.PENDING, value: 1 },
              { label: WorkshopStatusEnum.APPROVED, value: 2 },
              { label: WorkshopStatusEnum.SCHEDULED, value: 3 },
              { label: WorkshopStatusEnum.COMPLETED, value: 4 },
              { label: WorkshopStatusEnum.CANCELED, value: 5 },
              { label: WorkshopStatusEnum.POSTPONED, value: 6 },
            ]}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
};
