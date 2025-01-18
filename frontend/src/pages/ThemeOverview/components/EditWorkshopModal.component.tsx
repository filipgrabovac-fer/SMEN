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
  selectedWorkshopId?: number;
  setSelectedWorkshopId: Dispatch<SetStateAction<number | undefined>>;
  isModalOpen: boolean | undefined;
  setIsModalOpen: Dispatch<SetStateAction<boolean | undefined>>;
  workshopTitle: string;
  workshopDescription: string;
};

export const EditWorkshopModal = ({
  workshopStatusId,
  selectedWorkshopId,
  setSelectedWorkshopId,
  isModalOpen,
  setIsModalOpen,
  workshopTitle,
  workshopDescription,
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
        form.validateFields().then((values) => {
          putworkshop({
            workshopTitle: values.workshopTitle ?? workshopTitle,
            workshopDescription:
              values.workshopDescription ?? workshopDescription,
            workshopId: selectedWorkshopId ?? 0,
            workshopStatusId: values.workshopStatusId,
          });

          form.resetFields();
        });
      }}
    >
      <Form
        form={form}
        initialValues={{
          workshopTitle: workshopTitle,
          workshopDescription: workshopDescription,
        }}
        layout="vertical"
      >
        <Form.Item name="workshopTitle">
          <Input allowClear placeholder="ime radionice" />
        </Form.Item>
        <Form.Item name="workshopDescription">
          <TextArea allowClear placeholder="opis radionice" rows={6} />
        </Form.Item>
        <Form.Item name="workshopStatusId">
          <Select
            defaultActiveFirstOption={true}
            defaultValue={workshopStatusId}
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
