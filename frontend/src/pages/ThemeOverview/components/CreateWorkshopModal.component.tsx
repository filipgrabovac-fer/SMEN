import { Form, Input, Modal } from "antd";
import { Dispatch, SetStateAction } from "react";
import { usePostWorkshop } from "../hooks/usePostWorkshop.hook";
import { themeOverviewRoute } from "../../../routes/theme-overview/theme-overview.routes";
import { useQueryClient } from "@tanstack/react-query";
import TextArea from "antd/es/input/TextArea";

export type CreateWorkshopModalProps = {
  isCreateWorkshopModalOpen: boolean;
  setIsCreateWorkshopModalOpen: Dispatch<SetStateAction<boolean>>;
};
export const CreateWorkshopModal = ({
  isCreateWorkshopModalOpen,
  setIsCreateWorkshopModalOpen,
}: CreateWorkshopModalProps) => {
  const [form] = Form.useForm();
  const queryClient = useQueryClient();

  const { mutate: postWorkshop } = usePostWorkshop({
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["workshops"] });
    },
  });

  const { themeId } = themeOverviewRoute.useParams();
  const handleOk = () => {
    form.validateFields().then((values) => {
      postWorkshop({
        workshopTitle: values.workshopTitle,
        workshopDescription: values.workshopDescription,
        subjectId: themeId,
        workshopDateOfEvent: values.dateOfWorkshopEvent,
      });

      form.resetFields();
      setIsCreateWorkshopModalOpen(false);
    });
  };

  return (
    <Modal
      centered
      title="Dodaj novu radionicu"
      open={isCreateWorkshopModalOpen}
      onCancel={() => setIsCreateWorkshopModalOpen(false)}
      onOk={handleOk}
    >
      <Form form={form} layout="vertical">
        <Form.Item
          label="Naslov radionice"
          name="workshopTitle"
          rules={[{ required: true, message: "Please enter the title!" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Opis radionice"
          name="workshopDescription"
          rules={[{ required: true, message: "Please enter the description!" }]}
        >
          <TextArea />
        </Form.Item>
        <Form.Item
          label="Datum održavanja"
          name="dateOfWorkshopEvent"
          rules={[{ required: true, message: "Please select a date!" }]}
        >
          <Input type="date" />
        </Form.Item>
      </Form>
    </Modal>
  );
};
