import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { ButtonModule } from 'primeng/button';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { DialogModule } from 'primeng/dialog';
import { DropdownModule } from 'primeng/dropdown';
import { InputTextModule } from 'primeng/inputtext';
import { PaginatorModule } from 'primeng/paginator';
import { TableModule } from 'primeng/table';
import { ToastModule } from 'primeng/toast';
import { TooltipModule } from 'primeng/tooltip';


@NgModule({
    exports: [
        CommonModule,
        ToastModule,
        TableModule,
        ButtonModule,
        TooltipModule,
        ConfirmDialogModule,
        DialogModule,
        ReactiveFormsModule,
        DropdownModule,
        InputTextModule,
        PaginatorModule
    ],
})
export class SharedModule { }
