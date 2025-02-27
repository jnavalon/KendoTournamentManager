import {Component, OnInit, ViewChild} from '@angular/core';
import {BasicTableData} from "../../components/basic/basic-table/basic-table-data";
import {MatPaginator} from "@angular/material/paginator";
import {MatTable, MatTableDataSource} from "@angular/material/table";
import {MatSort} from "@angular/material/sort";
import {Participant} from "../../models/participant";
import {MatDialog} from "@angular/material/dialog";
import {MessageService} from "../../services/message.service";
import {ParticipantService} from "../../services/participant.service";
import {SelectionModel} from "@angular/cdk/collections";
import {ParticipantDialogBoxComponent} from "./participant-dialog-box/participant-dialog-box.component";
import {ClubService} from "../../services/club.service";
import {Club} from "../../models/club";
import {Action} from "../../action";

@Component({
  selector: 'app-participant-list',
  templateUrl: './participant-list.component.html',
  styleUrls: ['./participant-list.component.scss']
})
export class ParticipantListComponent implements OnInit {

  basicTableData: BasicTableData<Participant> = new BasicTableData<Participant>();
  clubs: Club[];

  @ViewChild(MatPaginator, {static: true}) paginator!: MatPaginator;
  @ViewChild(MatTable, {static: true}) table: MatTable<any>;
  @ViewChild(MatSort, {static: true}) sort!: MatSort;

  constructor(private participantService: ParticipantService, public dialog: MatDialog, private messageService: MessageService,
              private clubService: ClubService) {
    this.basicTableData.columns = ['id', 'idCard', 'name', 'lastname', 'clubName'];
    this.basicTableData.columnsTags = ['id', 'idCard', 'name', 'lastname', 'club'];
    this.basicTableData.visibleColumns = ['name', 'lastname', 'clubName'];
    this.basicTableData.selection = new SelectionModel<Participant>(false, []);
    this.basicTableData.dataSource = new MatTableDataSource<Participant>();
  }

  ngOnInit(): void {
    this.clubService.getAll().subscribe(clubs => {
      this.clubs = clubs
    });
    this.showAllElements();
  }

  showAllElements(): void {
    this.participantService.getAll().subscribe(participants => {
      this.basicTableData.dataSource.data = participants.map(participant => Participant.clone(participant));
    });
  }

  addElement(): void {
    const participant = new Participant();
    this.openDialog('Add a new club', Action.Add, participant);
  }

  editElement(): void {
    if (this.basicTableData.selectedElement) {
      this.openDialog('Edit club', Action.Update, this.basicTableData.selectedElement);
    }
  }

  deleteElement(): void {
    if (this.basicTableData.selectedElement) {
      this.openDialog('Delete club', Action.Delete, this.basicTableData.selectedElement);
    }
  }

  setSelectedItem(row: Participant): void {
    if (row === this.basicTableData.selectedElement) {
      this.basicTableData.selectedElement = undefined;
    } else {
      this.basicTableData.selectedElement = row;
    }
  }

  openDialog(title: string, action: Action, participant: Participant) {
    const dialogRef = this.dialog.open(ParticipantDialogBoxComponent, {
      width: '250px',
      data: {
        title: title, action: action, entity: participant,
        clubs: this.clubs
      }
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result.action == Action.Add) {
        this.addRowData(result.data);
      } else if (result.action == Action.Update) {
        this.updateRowData(result.data);
      } else if (result.action == Action.Delete) {
        this.deleteRowData(result.data);
      }
    });
  }

  addRowData(participant: Participant) {
    this.participantService.add(participant).subscribe(participant => {
      this.basicTableData.dataSource.data.push(participant);
      this.basicTableData.dataSource._updateChangeSubscription();
      this.messageService.infoMessage("Participant Stored");
    });
  }

  updateRowData(participant: Participant) {
    this.participantService.update(participant).subscribe(() => {
        this.messageService.infoMessage("Participant Updated");
      }
    );
  }

  deleteRowData(participant: Participant) {
    this.participantService.delete(participant).subscribe(() => {
        this.basicTableData.dataSource.data = this.basicTableData.dataSource.data.filter(existing_Participant => existing_Participant !== participant);
        this.messageService.infoMessage("Participant Deleted");
      }
    );
  }

}
