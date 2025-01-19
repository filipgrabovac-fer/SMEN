import { Form, Input, Modal } from "antd";
import TextArea from "antd/es/input/TextArea";
import { Dispatch, SetStateAction } from "react";
import { useEditTheme } from "../hooks/useEditTheme.hook";
import { useQueryClient } from "@tanstack/react-query";

export type EditThemeModalProps = {
  title: string;
  description: string;
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
        form.validateFields().then((values) => {
          putTheme({
            themeTitle: values.themeTitle ?? title,
            themeDescription: values.themeDescription ?? description,
            subjectId: selectedThemeId ?? 0,
          });

          form.resetFields();
        });
      }}
    >
      <Form form={form} layout="vertical">
        <Form.Item name="themeTitle">
          <Input
            allowClear
            title="ime teme"
            placeholder="ime teme"
            defaultValue={title}
          />
        </Form.Item>
        <Form.Item name="themeDescription">
          <TextArea
            allowClear
            title="opis teme"
            placeholder="opis teme"
            rows={6}
            defaultValue={description}
          />
        </Form.Item>
      </Form>
    </Modal>
  );
};
