import { Form, Input, Modal } from "antd";
import TextArea from "antd/es/input/TextArea";
import { Dispatch, SetStateAction } from "react";
import { useEditTheme } from "../hooks/useEditTheme.hook";
import { useQueryClient } from "@tanstack/react-query";

export type EditThemeModalProps = {
  title: string;
  setTitle: Dispatch<SetStateAction<string | undefined>>;
  description: string;
  setDescription: Dispatch<SetStateAction<string | undefined>>;
  selectedThemeId?: number;
  setSelectedThemeId: Dispatch<SetStateAction<number | undefined>>;
  isModalOpen: boolean | undefined;
  setIsModalOpen: Dispatch<SetStateAction<boolean>>;
};

export const EditThemeModal = ({
  selectedThemeId,
  setSelectedThemeId,
  isModalOpen,
  setIsModalOpen,
  title,
  description,
  setTitle,
  setDescription,
}: EditThemeModalProps) => {
  const [form] = Form.useForm();

  const queryClient = useQueryClient();
  const { mutate: putTheme } = useEditTheme({
    onSuccess: () => {
      setSelectedThemeId(undefined);
      setIsModalOpen(false);
      queryClient.invalidateQueries({ queryKey: ["themes"] });
    },
  });

  return (
    <Modal
      title="Uredi temu"
      centered
      open={isModalOpen}
      onCancel={() => {
        setSelectedThemeId(undefined);
        setIsModalOpen(false);
      }}
      onOk={() => {
        form.validateFields().then(() => {
          putTheme({
            themeTitle: title,
            themeDescription: description,
            subjectId: selectedThemeId ?? 0,
          });

          form.resetFields();
        });
      }}
    >
      <Form form={form} layout="vertical">
        <Form.Item>
          <Input
            required
            allowClear
            title="ime teme"
            placeholder="ime teme"
            value={title}
            onChange={(e) => setTitle(e.target.value)}
          />
        </Form.Item>
        <Form.Item>
          <TextArea
            required
            allowClear
            title="opis teme"
            placeholder="opis teme"
            rows={6}
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
};
