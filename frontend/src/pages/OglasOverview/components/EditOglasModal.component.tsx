import { useQueryClient } from "@tanstack/react-query";
import { Form, Input, Modal } from "antd";
import { Dispatch, SetStateAction } from "react";
import { usePutOglas } from "../hooks/usePutOglas.hook";

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

  const queryClient = useQueryClient();

  const { mutate: putOglas } = usePutOglas({
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["posts"] });
      setSelectedOglasId(undefined);
    },
  });
  return (
    <Modal
      title="Uredi temu"
      centered
      open={selectedOglasId !== undefined}
      onOk={() => {
        form.validateFields().then((values) => {
          putOglas({
            postId: selectedOglasId ?? 0,
            title: values.postTitle ?? title,
            description: values.postDescription ?? description,
          });
        });
      }}
      onCancel={() => setSelectedOglasId(undefined)}
      className="bg-none"
    >
      <Form form={form} layout="vertical">
        <Form.Item label="Naslov oglasa" name="postTitle">
          <Input defaultValue={title} />
        </Form.Item>
        <Form.Item label="Opis oglasa" name="postDescription">
          <Input defaultValue={description} />
        </Form.Item>
      </Form>
    </Modal>
  );
};
