import { Button, Input, Menu, Modal } from "antd";
import TextArea from "antd/es/input/TextArea";
import { Dispatch, SetStateAction, useState } from "react";
import { usePostNewTheme } from "../hooks/usePostNewTheme.hook";
import { useQueryClient } from "@tanstack/react-query";

export type CreateThemeModalProps = {
  isModalOpen: boolean;
  setIsModalOpen: Dispatch<SetStateAction<boolean>>;
};
export const CreateThemeModal = ({
  isModalOpen,
  setIsModalOpen,
}: CreateThemeModalProps) => {
  const [themeName, setThemeName] = useState<string>("");
  const [themeDescription, setThemeDescription] = useState<string>("");

  const queryClient = useQueryClient();
  const { mutate: postNewTheme } = usePostNewTheme({
    onSuccess: () => {
      setIsModalOpen(false);
      queryClient.invalidateQueries({ queryKey: ["themes"] });
    },
  });

  return (
    <Modal
      centered
      open={isModalOpen}
      footer={null}
      onCancel={() => setIsModalOpen(false)}
    >
      <p className="text-2xl font-bold">Dodaj temu</p>
      <div className="flex flex-col gap-y-6 items-center mt-8">
        <Input
          allowClear
          title="ime teme"
          placeholder="ime teme"
          onChange={(e) => setThemeName(e.target.value)}
        />
        <TextArea
          allowClear
          title="opis teme"
          placeholder="opis teme"
          rows={6}
          onChange={(e) => setThemeDescription(e.target.value)}
        />
        <Menu title="tip" />
        <Button
          onClick={() => {
            postNewTheme({
              description: themeDescription,
              id: 1,
              tags: "",
              title: themeName,
            });
          }}
          disabled={themeName == "" || themeDescription == ""}
          className="w-max bg-button_border text-white"
        >
          Spremi promjene
        </Button>
      </div>
    </Modal>
  );
};
