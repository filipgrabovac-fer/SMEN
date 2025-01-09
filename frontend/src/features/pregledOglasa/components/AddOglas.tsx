import { Modal, Form, Input, DatePicker } from "antd";
import { Oglas } from "../typings/oglas";

interface AddOglasModalProps {
  visible: boolean;
  onOk: (oglas: Oglas) => void;
  onCancel: () => void;
}

const AddOglasModal = ({ visible, onOk, onCancel }: AddOglasModalProps) => {
  const [form] = Form.useForm();

  const handleOk = () => {
    form.validateFields().then((values) => {
      const newOglas: Oglas = {
        key: Date.now().toString(), // Generate a unique key
        name: values.name,
        opis: values.opis,
        datum: values.datum.toDate(),
        naslovOglasa: values.naslovOglasa,
        details: "#", // Placeholder
      };
      onOk(newOglas); // Pass the new oglas to the parent
      form.resetFields();
    });
  };

  return (
    <Modal
      title="Add New Oglas"
      open={visible}
      onOk={handleOk}
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
          label="Name"
          name="name"
          rules={[{ required: true, message: "Please enter the name!" }]}
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
        <Form.Item
          label="Datum"
          name="datum"
          rules={[{ required: true, message: "Please select the date!" }]}
        >
          <DatePicker />
        </Form.Item>
      </Form>
    </Modal>
  );
};

export default AddOglasModal;
