import { Button, Table } from "antd";
import { WorkshopApplicationModal } from "./components/WorkshopApplicationModal.component";
import { useState } from "react";
import { useGetWorkshopsForTheme } from "./hooks/useGetWorkshopsForTheme.hook";
import { useGetThemeDetails } from "./hooks/useGetThemeDetails.hook";
import { themeOverviewRoute } from "../../routes/theme-overview/theme-overview.routes";
import { PencilIcon, PlusIcon, TrashIcon } from "@heroicons/react/24/solid";
import { CreateWorkshopModal } from "./components/CreateWorkshopModal.component";

import { useQueryClient } from "@tanstack/react-query";
import { useDeleteWorkshop } from "./hooks/useDeleteWorkshop.hook";
import { EditWorkshopModal } from "./components/EditWorkshopModal.component";

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
  { title: "", dataIndex: "edit", key: "edit" },
];

export const ThemeOverview = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [selectedWorkshopId, setSelectedWorkshopId] = useState<
    number | undefined
  >();
  const [isCreateWorkshopModalOpen, setIsCreateWorkshopModalOpen] =
    useState(false);

  const [isEditThemeModalOpen, setIsEditThemeModalOpen] = useState<
    boolean | undefined
  >();

  const { themeId } = themeOverviewRoute.useParams();
  const { data } = useGetWorkshopsForTheme({ subjectId: themeId });
  const { data: themeDetails } = useGetThemeDetails({ subjectId: themeId });
  const queryClient = useQueryClient();

  const { mutate: postDeleteWorkshop } = useDeleteWorkshop({
    onSuccess: () => queryClient.invalidateQueries({ queryKey: ["workshops"] }),
  });

  const dataSource = data?.map((workshop) => ({
    key: workshop.id,
    naziv: workshop.title,
    datum_odrzavanja: workshop.dateOfEvent,
    broj_mjesta: workshop.noOfAvailableSlots,
    opis: workshop.description,
    status: workshop.workshopStatus,
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
    edit: (
      <div className="flex gap-x-2">
        <Button
          type="primary"
          onClick={() => {
            setSelectedWorkshopId(workshop.id);
            setIsEditThemeModalOpen(true);
          }}
        >
          <PencilIcon className="w-3 h-3" />
        </Button>
        <Button
          className="p-auto"
          onClick={() => postDeleteWorkshop({ workshopId: workshop.id })}
        >
          <TrashIcon className="w-4 h-4" color="red" />
        </Button>
        <EditWorkshopModal
          workshopStatusId={Number(workshop.workshopStatusId)}
          isModalOpen={isEditThemeModalOpen}
          setIsModalOpen={setIsEditThemeModalOpen}
          selectedWorkshopId={selectedWorkshopId}
          setSelectedWorkshopId={setSelectedWorkshopId}
          workshopTitle={workshop.title}
          workshopDescription={workshop.description}
        />
      </div>
    ),
  }));

  return (
    <>
      <div className="bg-gray-100 flex gap-y-8 p-8 justify-between">
        <div>
          <p className="text-2xl font-medium">{themeDetails?.title}</p>
          <p>{themeDetails?.description}</p>
        </div>
        <div className="mt-auto">
          <Button
            type="primary"
            onClick={() => setIsCreateWorkshopModalOpen(true)}
          >
            Dodaj radionicu <PlusIcon className="w-5 h-5" />
          </Button>
        </div>
      </div>
      <Table
        columns={columns}
        dataSource={dataSource}
        className="w-4/5 m-auto mt-[30px]"
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
