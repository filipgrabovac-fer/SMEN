import { Button, Table } from "antd";
import { WorkshopApplicationModal } from "./components/WorkshopApplicationModal.component";
import { useState } from "react";
import { useGetWorkshopsForTheme } from "./hooks/useGetWorkshopsForTheme.hook";
import { useGetThemeDetails } from "./hooks/useGetThemeDetails.hook";
import { themeOverviewRoute } from "../../routes/theme-overview/theme-overview.routes";
import { PlusIcon } from "@heroicons/react/24/solid";
import { CreateWorkshopModal } from "./components/CreateWorkshopModal.component";

const columns = [
  {
    title: "naziv",
    dataIndex: "naziv",
    key: "naziv",
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
    title: "opis",
    dataIndex: "opis",
    key: "opis",
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
];

export const ThemeOverview = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedWorkshopId, setSelectedWorkshopId] = useState<
    number | undefined
  >();
  const [isCreateWorkshopModalOpen, setIsCreateWorkshopModalOpen] =
    useState(false);

  const { themeId } = themeOverviewRoute.useParams();

  const { data } = useGetWorkshopsForTheme({ subjectId: themeId });
  const { data: themeDetails } = useGetThemeDetails({ subjectId: themeId });

  const dataSource = data?.map((workshop) => ({
    key: workshop.id,
    naziv: workshop.title,
    datum_odrzavanja: "2021-09-09",
    broj_mjesta: workshop.noOfAvailableSlots,
    opis: workshop.description,
    status: workshop.workshopStatusId,
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
  }));

  return (
    <>
      <div className="bg-gray-100 flex gap-y-8 p-8 justify-between">
        <div>
          <p className="text-2xl font-medium">{themeDetails?.title}</p>
          <p>{themeDetails?.description}</p>
        </div>
        <Button
          type="primary"
          className="mt-auto"
          onClick={() => setIsCreateWorkshopModalOpen(true)}
        >
          Dodaj radionicu <PlusIcon className="w-5 h-5" />
        </Button>
      </div>
      <Table
        columns={columns}
        dataSource={dataSource}
        className="w-4/5 m-auto mt-[-20px]"
      />
      {selectedWorkshopId && (
        <WorkshopApplicationModal
          isModalOpen={isModalOpen}
          setIsModalOpen={setIsModalOpen}
          selectedWorkshopId={selectedWorkshopId}
          // @ts-expect-error random error, works without it
          setSelectedWorkshopId={setSelectedWorkshopId}
        />
      )}

      <CreateWorkshopModal
        isCreateWorkshopModalOpen={isCreateWorkshopModalOpen}
        setIsCreateWorkshopModalOpen={setIsCreateWorkshopModalOpen}
      />
    </>
  );
};
