import { Form, Input, InputNumber, Modal } from "antd";
import { Dispatch, SetStateAction } from "react";
import { useQueryClient } from "@tanstack/react-query";
import { usePostReview } from "../hooks/usePostReview.hook";

export type ReviewWorkshopModalProps = {
  isReviewWorkshopModalOpen: boolean;
  setIsReviewWorkshopModalOpen: Dispatch<SetStateAction<boolean>>;
  selectedWorkshopId: number | undefined;
};

export const ReviewWorkshopModal = ({
  isReviewWorkshopModalOpen,
  setIsReviewWorkshopModalOpen,
  selectedWorkshopId,
}: ReviewWorkshopModalProps) => {
  const userId = Number(localStorage.getItem("userId") ?? 0);

  const [form] = Form.useForm();
  const queryClient = useQueryClient();

  const { mutate: postWorkshopReview } = usePostReview({
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ["workshops"] });
    },
  });

  const handleOk = () => {
    form.validateFields().then((values) => {
      if (selectedWorkshopId !== undefined) {
        postWorkshopReview({
          rating: values.rating,
          comment: values.comment,
          userId: userId,
          workshopId: selectedWorkshopId,
        });

        form.resetFields();
        setIsReviewWorkshopModalOpen(false);
      } else {
        console.error("Selected workshop ID is undefined");
      }
    });
  };

  return (
    <Modal
      centered
      title="Ocijeni radionicu"
      open={isReviewWorkshopModalOpen}
      onCancel={() => setIsReviewWorkshopModalOpen(false)}
      onOk={handleOk}
    >
      <Form form={form} layout="vertical">
        <Form.Item
          label="Ocjena (1-5)"
          name="rating"
          rules={[{ required: true, message: "Unesite ocjenu!" }]}
        >
          <InputNumber min={1} max={5} />
        </Form.Item>
        <Form.Item
          label="Komentar"
          name="comment"
          rules={[{ required: true, message: "Unesite komentar!" }]}
        >
          <Input.TextArea rows={4} />
        </Form.Item>
      </Form>
    </Modal>
  );
};
