import { Form, Input, Modal } from "antd";
import TextArea from "antd/es/input/TextArea";
import { Dispatch, SetStateAction, useState } from "react";
import { useEditTheme } from "../hooks/useEditTheme.hook";

export type EditThemeModalProps = {
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
}: EditThemeModalProps) => {
  const [themeTitle, setThemeTitle] = useState<string>();
  const [themeDescription, setThemeDescription] = useState<string>();

  const [form] = Form.useForm();
  const { mutate: putTheme } = useEditTheme({
    onSuccess: () => {
      setSelectedThemeId(undefined);
      setIsModalOpen(false);
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
            themeTitle: themeTitle ?? "",
            themeDescription: themeDescription ?? "",
            subjectId: selectedThemeId ?? 0,
          });

          form.resetFields();
        });
      }}
    >
      <Form.Item required name="themeTitle">
        <Input
          allowClear
          title="ime teme"
          placeholder="ime teme"
          value={themeTitle}
          onChange={(e) => setThemeTitle(e.target.value)}
        />
      </Form.Item>
      <Form.Item>
        <TextArea
          allowClear
          title="opis teme"
          placeholder="opis teme"
          rows={6}
          value={themeDescription}
          onChange={(e) => setThemeDescription(e.target.value)}
        />
      </Form.Item>
    </Modal>
  );
};
