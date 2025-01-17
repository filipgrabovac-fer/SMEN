import { useState } from "react";
import { useGetWorkshopsForUser } from "./hooks/useGetWorkshopsForUser.hooks";
import { WorkshopApplicationModal } from "../ThemeOverview/components/WorkshopApplicationModal.component";

import { Table } from "antd";
import { ReviewWorkshopModal } from "./components/WorkshopUserReview";

const userId = 1;

const columns = [
  {
    title: "title",
    dataIndex: "title",
    key: "title",
  },
  {
    title: "datum održavanja",
    dataIndex: "datum_odrzavanja",
    key: "datum_odrzavanja",
  },
  {
    title: "broj mjesta",
    dataIndex: "broj_mjesta",
    key: "broj_mjesta",
  },
  {
    title: "status",
    dataIndex: "status",
    key: "status",
  },
  {
    title: "",
    dataIndex: "find_out_more",
    key: "find_out_more",
  },
  {
    title: "",
    dataIndex: "review",
    key: "review",
  },
];

export const WorkshopUserOverview = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [isreviewModalOpen, setIsReviewModalOpen] = useState(false);
  const [selectedWorkshopId, setSelectedWorkshopId] = useState<
    number | undefined
  >();

  const { data } = useGetWorkshopsForUser({ userId: userId });
  const dataSource = data?.map((workshop) => ({
    key: workshop.id,
    title: workshop.title,
    datum_odrzavanja: "2021-09-09",
    broj_mjesta: workshop.noOfAvailableSlots,
    status: workshop.workshopStatusName,
    find_out_more: (
      <p
        className="underline text-blue-900 cursor-pointer"
        onClick={() => {
          setSelectedWorkshopId(workshop.id);
          setIsModalOpen(true);
        }}
      >
        Saznaj više
      </p>
    ),
    review:
      workshop.workshopStatusName === "Completed" ? (
        <p
          className="underline text-blue-900 cursor-pointer"
          onClick={() => {
            setSelectedWorkshopId(workshop.id);
            setIsReviewModalOpen(true);
          }}
        >
          Ocijeni
        </p>
      ) : null,
  }));

  return (
    <div className="w-4/5 m-auto flex flex-col gap-y-6 mt-6">
      <div className="flex justify-between">
        <h1 className="text-2xl font-medium">Moje radionice</h1>
      </div>
      <Table
        columns={columns}
        dataSource={dataSource}
        className="w-4/5 m-auto mt-[-20px]"
      />
      {selectedWorkshopId && isModalOpen && (
        <WorkshopApplicationModal
          isModalOpen={isModalOpen}
          setIsModalOpen={setIsModalOpen}
          selectedWorkshopId={selectedWorkshopId}
          // @ts-expect-error random error, works without it
          setSelectedWorkshopId={setSelectedWorkshopId}
        />
      )}
      {selectedWorkshopId && isreviewModalOpen && (
        <ReviewWorkshopModal
          isReviewWorkshopModalOpen={isreviewModalOpen}
          setIsReviewWorkshopModalOpen={setIsReviewModalOpen}
          selectedWorkshopId={selectedWorkshopId}
        />
      )}
    </div>
  );
};
