import { Modal, Form, Input } from "antd";
import { usePostNewPost } from "../hooks/usePostNewPost.hook";
import { useQueryClient } from "@tanstack/react-query";

interface AddOglasModalProps {
  visible: boolean;
  onCancel: () => void;
}

export type NewPostType = {
  description: string;
  tags: string;
  title: string;
};

const AddOglasModal = ({ visible, onCancel }: AddOglasModalProps) => {
  const [form] = Form.useForm();
  const queryClient = useQueryClient();

  const { mutate: postNewPost } = usePostNewPost({
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["posts"] });
      onCancel();
    },
  });
  const handleOk = () => {
    form.validateFields().then((values) => {
      postNewPost({
        description: values.opis,
        tags: "tags",
        title: values.naslovOglasa,
      });

      form.resetFields();
    });
  };

  return (
    <Modal
      title="Dodaj novi oglas"
      open={visible}
      onOk={handleOk}
      centered
      onCancel={onCancel}
    >
      <Form form={form} layout="vertical">
        <Form.Item
          label="Naslov Oglasa"
          name="naslovOglasa"
          rules={[{ required: true, message: "Please enter the title!" }]}
        >
          <Input />
        </Form.Item>
        <Form.Item
          label="Opis"
          name="opis"
          rules={[{ required: true, message: "Please enter the description!" }]}
        >
          <Input />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default AddOglasModal;
