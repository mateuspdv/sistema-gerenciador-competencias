import { Directive, ElementRef } from '@angular/core';

@Directive({
  standalone: true,
  selector: '[appFormAutoFocus]',
  exportAs: 'focusInvalidInput'
})
export class FormAutoFocusDirective {

  constructor(private elementRef: ElementRef) { }

  focus(): void {
    const invalidControl = this.elementRef.nativeElement.querySelector('.ng-invalid');
    if (invalidControl) {
      invalidControl.focus();
      invalidControl.blur();
      invalidControl.focus();
    }
  }

}
