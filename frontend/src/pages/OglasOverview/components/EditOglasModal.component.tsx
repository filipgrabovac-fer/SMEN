import { Form, Input, Modal } from "antd";
import { Dispatch, SetStateAction } from "react";

export type EditOglasModalProps = {
  title: string;
  description: string;
  selectedOglasId?: number;
  setSelectedOglasId: Dispatch<SetStateAction<number | undefined>>;
};
export const EditOglasModal = ({
  title,
  description,
  setSelectedOglasId,
  selectedOglasId,
}: EditOglasModalProps) => {
  const [form] = Form.useForm();
  return (
    <Modal
      title="Uredi temu"
      centered
      open={selectedOglasId !== undefined}
      onOk={() => {
        form.validateFields().then((values) => {
          console.log(values);
          setSelectedOglasId(undefined);
        });
      }}
      onCancel={() => setSelectedOglasId(undefined)}
      className="bg-none"
    >
      <Form>
        <Form.Item label="Naslov teme" name="themeTitle">
          <Input value={title} />
        </Form.Item>
        <Form.Item label="Opis teme" name="themeDescription">
          <Input value={description} />
        </Form.Item>
      </Form>
    </Modal>
  );
};
